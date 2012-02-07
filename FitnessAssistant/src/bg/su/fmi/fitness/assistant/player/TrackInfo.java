package bg.su.fmi.fitness.assistant.player;

public class TrackInfo {
	
	private String path;
	private String trackName;
	
	public TrackInfo(String trackName, String path) {
		this.trackName = trackName;
		this.path = path;
	}
	
	public String getTrackName() {
		return trackName;		
	}
	
	public String getPath() {
		return path;		
	}

}
