package se.divdev.epever;

import se.divdev.epever.data.*;

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

    private Future readFuture;

    public ScheduledRegulatorCommunicator(final RegulatorCommunicator regulatorCommunicator,
                                          final ScheduledExecutorService executorService) {
        this.regulatorCommunicator = regulatorCommunicator;
        this.executorService = executorService;
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

    private void read() {
        List<Class<?>> readFrom = new ArrayList<>(ALWAYS);
        if (readAllData.compareAndSet(true, false)) {
            readFrom.addAll(SOMETIMES);
        }
        readFrom.stream()
                .map(this::readRaw)
                .forEach(data::merge);

        notifyCallbacks();
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