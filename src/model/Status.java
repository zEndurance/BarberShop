package model;

public enum Status {

	WAITING {
		public String toString() {
			return "Waiting";
		}
	},

	CHECKIN {
		public String toString() {
			return "Checked in";
		}
	},

	NOTSHOWED {
		public String toString() {
			return "Not Showed";
		}
	},
	ARRIVED {
		public String toString() {
			return "Arrived";
		}
	},
	CANCELLED {
		public String toString() {
			return "Cancelled";
		}
	}

}
