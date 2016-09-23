package MyLab5;

public class Entry {
		private Integer key, value; // key and value

		public Entry(Integer k, Integer v) {
			key = k;
			value = v;
		}

		/**
		 * @return the key
		 */
		public Integer getKey() {
			return key;
		}

		/**
		 * @param key
		 *            the key to set
		 */
		public void setKey(Integer key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public Integer getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public Integer setValue(Integer value) {
			this.value = value;
			return value;
		}
	}