package util;

/**
 * @author sarahzhou
 * Transforms between location ratios (used in back end) to location coordinates (used in front end)
 */
public class Transformer {

public static double ratioToCoordinate(double ratio, double length) {
	return ratio*length;
}

public static double coordinateToRatio(double coordinate,double length) {
	return length/coordinate;
}
}
