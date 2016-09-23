
public class HuffmanElement {
	private Character character;
	private int frequency;

	public HuffmanElement(Character c, int freq) {
		character = c;
		frequency = freq;
	}

	/**
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
}