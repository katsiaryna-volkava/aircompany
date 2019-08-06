import planes.ExperimentalPlane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.Arrays;
import java.util.List;

public class AirportTest {
    private static List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000,
                    60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300,
                    63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100,
                    70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800,
                    65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800,
                    80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100,
                    30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500,
                    50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000,
                    60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000,
                    80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000,
                    70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000,
                    80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000,
                    10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000,
                    11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000,
                    110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482,
                    500, ExperimentalTypes.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307,
                    500, ExperimentalTypes.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private static PassengerPlane expectedPlaneWithMaxPassengersCapacity =
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    @Test
    public void getTransportMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();
        boolean isThereAnyNotTransportMilitaryPlane = false;
        for (MilitaryPlane militaryPlane : transportMilitaryPlanes) {
            if ((militaryPlane.getType() != MilitaryType.TRANSPORT)) {
                isThereAnyNotTransportMilitaryPlane = true;
                break;
            }
        }
        Assert.assertFalse(isThereAnyNotTransportMilitaryPlane);
    }

    @Test
    public void getPassengerPlaneWithMaxCapacity() {
        Airport airport = new Airport(planes);
        PassengerPlane planeWithMaxPassengerCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        Assert.assertEquals(planeWithMaxPassengerCapacity, expectedPlaneWithMaxPassengersCapacity);
    }

    @Test
    public void plainsAreSortedByMaxLoadCapacity() {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();

        boolean isNextPlaneMaxLoadCapacityHigherThanCurrent = true;
        for (int i = 0; i < planesSortedByMaxLoadCapacity.size() - 1; i++) {
            Plane currentPlane = planesSortedByMaxLoadCapacity.get(i);
            Plane nextPlane = planesSortedByMaxLoadCapacity.get(i + 1);
            if (currentPlane.getMaxLoadCapacity() > nextPlane.getMaxLoadCapacity()) {
                isNextPlaneMaxLoadCapacityHigherThanCurrent = false;
                break;
            }
        }
        Assert.assertTrue(isNextPlaneMaxLoadCapacityHigherThanCurrent);
    }

    @Test
    public void thereIsAtLeastOneBomberInMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> militaryPLanes = airport.getMilitaryPlanes();
        boolean isThereAnyBomber = false;
        for (MilitaryPlane militaryPlane : militaryPLanes) {
            if ((militaryPlane.getType() == MilitaryType.BOMBER)) {
                isThereAnyBomber = true;
                break;
            }
        }
        Assert.assertTrue(isThereAnyBomber);
    }

    @Test
    public void thereAreNoUnclassifiedExperimentalPlanes() {
        Airport airport = new Airport(planes);
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasUnclassifiedPlanes = false;
        for (ExperimentalPlane experimentalPlane : experimentalPlanes) {
            if (experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED) {
                hasUnclassifiedPlanes = true;
                break;
            }
        }
        Assert.assertFalse(hasUnclassifiedPlanes);
    }
}
