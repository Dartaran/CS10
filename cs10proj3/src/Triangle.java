public class Triangle implements GeomShape {
	private int baseX, baseY, baseWidth;
	private int topX, topY;

	/**
	 * Creates a triangle
	 * 
	 * @param bX
	 *            x coordinate of leftmost base point
	 * @param bY
	 *            y coordinate of leftmost base point
	 * @param bW
	 *            base width
	 * @param tX
	 *            top x coordinate
	 * @param tY
	 *            top y coordinate
	 */
	public Triangle(int bX, int bY, int bW, int tX, int tY) {
		baseX = bX;
		baseY = bY;
		baseWidth = bW;
		topX = tX;
		topY = tY;
	}

	/**
	 * Moves triangle by specified x and y
	 * 
	 * @param deltaX
	 *            the amount to move in the x direction
	 * @param deltaY
	 *            the amount to move in the y direction
	 */
	public void move(int deltaX, int deltaY) {
		baseX += deltaX;
		baseY += deltaY;
		topX += deltaX;
		topY += deltaY;
	}

	/**
	 * Scales the triangle
	 * 
	 * @param factor
	 *            the scaling factor
	 */
	public void scale(double factor) {
		baseWidth *= factor;
		topY = (int) ((topY - baseY) * factor) + baseY;
	}

	/**
	 * @return the area of the triangle
	 */
	public double areaOf() {
		return (baseWidth * (topY - baseY)) / 2;
	}

	public String toString() {
		return "Triangle with vertices (" + baseX + ", " + baseY + "), (" + (baseX + baseWidth) + ", " + baseY + "), ("
				+ topX + ", " + topY + ")";
	}
}