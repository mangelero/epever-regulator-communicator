package se.divdev.epever.core;

import se.divdev.epever.api.RegulatorCommunicator;
import se.divdev.epever.api.RegulatorException;
import se.divdev.epever.api.RegulatorRawData;
import se.divdev.epever.api.data.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ScheduledRegulatorCommunicator implements RegulatorCommunicator {

    private static final List<Class<?>> ALWAYS = Arrays.asList(
            RealtimeData.class,
            RealtimeStatus.class,
            StatisticalParameter.class,
            SettingParameter.class
    );

    private static final List<Class<?>> SOMETIMES = Arrays.asList(
            BatterySettingParameter.class,
            Coils.class,
            DiscreteInput.class,
            RatedData.class
    );


    private final AtomicBoolean readAllData = new AtomicBoolean(true);

    private final RegulatorRawData data = new RegulatorRawData();

    private final RegulatorCommunicator regulatorCommunicator;

    private final ScheduledExecutorService executorService;

    private final List<Consumer<RegulatorRawData>> callbacks = new ArrayList<>();

    private final boolean keepAlive;

    private Future readFuture;

    /**
     * Construct the scheduled regulator communicator service
     *
     * @param regulatorCommunicator The underlying regulator communicator to use
     * @param executorService       Executor service to be used
     */
    public ScheduledRegulatorCommunicator(final RegulatorCommunicator regulatorCommunicator,
                                          final ScheduledExecutorService executorService) {
        this(regulatorCommunicator, executorService, true);
    }

    /**
     * Construct the scheduled regulator communicator service
     *
     * @param regulatorCommunicator The underlying regulator communicator to use
     * @param executorService       Executor service to be used
     * @param keepAlive             Set to true to keep the connection to the controller alive between reads. Set to false if log is spammed with timeout exceptions
     */
    public ScheduledRegulatorCommunicator(final RegulatorCommunicator regulatorCommunicator,
                                          final ScheduledExecutorService executorService,
                                          final boolean keepAlive) {
        this.regulatorCommunicator = regulatorCommunicator;
        this.executorService = executorService;
        this.keepAlive = keepAlive;
    }

    /**
     * Add a callback consumer that should be called when new data has been read
     *
     * @param callback Consumer that will be called
     */
    public void addCallback(final Consumer<RegulatorRawData> callback) {
        synchronized (callback) {
            callbacks.add(callback);
            notifyCallback(callback);
        }
    }

    private void notifyCallbacks() {
        synchronized (callbacks) {
            callbacks.forEach(this::notifyCallback);
        }
    }

    private void notifyCallback(final Consumer<RegulatorRawData> callback) {
        if (data.isEmpty()) {
            return;
        }
        executorService.submit(() -> callback.accept(data));
    }

    private void connectSilent() {
        try {
            if (!isConnected()) {
                connect();
            }
        } catch (RegulatorException.ConnectException e) {
            LOGGER.error("Failed to connect", e);
            throw new RuntimeException(e);
        }
    }

    private void disconnectSilent() {
        if (keepAlive) {
            return;
        }
        try {
            disconnect();
        } catch (RegulatorException.DisconnectException e) {
            LOGGER.error("Failed to disconnect", e);
            throw new RuntimeException(e);
        }
    }

    private void read() {
        connectSilent();
        List<Class<?>> readFrom = new ArrayList<>(ALWAYS);
        if (readAllData.compareAndSet(true, false)) {
            readFrom.addAll(SOMETIMES);
        }
        readFrom.stream()
                .map(this::readRaw)
                .forEach(data::merge);

        notifyCallbacks();
        disconnectSilent();
    }

    /**
     * Connect and start the scheduled regulator communicator and read at given interval
     *
     * @param interval The interval to perform reads
     * @throws RegulatorException
     */
    public void start(final Duration interval) throws RegulatorException {
        connect();
        readFuture = executorService.scheduleAtFixedRate(this::read, 0, interval.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Stop the scheduled regulator communicator and disconnect
     *
     * @throws RegulatorException
     */
    public void stop() throws RegulatorException {
        if (readFuture != null) {
            readFuture.cancel(true);
        }
        disconnect();
    }

    @Override
    public void setRetryAttempts(int retries) {
        regulatorCommunicator.setRetryAttempts(retries);
    }

    @Override
    public void setSleepBetweenRetriesMs(int sleep) {
        regulatorCommunicator.setSleepBetweenRetriesMs(sleep);
    }

    @Override
    public boolean isConnected() {
        return regulatorCommunicator.isConnected();
    }

    @Override
    public void connect() throws RegulatorException.ConnectException {
        regulatorCommunicator.connect();
    }

    @Override
    public void disconnect() throws RegulatorException.DisconnectException {
        regulatorCommunicator.disconnect();
    }

    @Override
    public int[] read(int startAddress, int quantity) {
        return regulatorCommunicator.read(startAddress, quantity);
    }

    @Override
    public boolean write(int[] data, int startAddress) {
        readAllData.set(true);
        return regulatorCommunicator.write(data, startAddress);
    }
}
