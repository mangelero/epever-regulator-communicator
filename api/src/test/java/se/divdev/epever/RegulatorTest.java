package se.divdev.epever;

import se.divdev.epever.data.Coils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegulatorTest {

    @Test
    public void testRegulatorUtils() {
        List<Regulator.ClusterInformation> clusters = Regulator.clusterInformation(Coils.class);
        clusters.forEach(System.out::println);
        assertEquals(2, clusters.size());
    }

}
