package modelNotepad;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModelPad {
	
	private String title;
	private String content;
	private LocalDateTime timestamp;

	public ModelPad(String title, String content, LocalDateTime timestamp) {
		this.title = title;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	//==========================================
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		updateTimestamp();
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
		updateTimestamp();
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	//==========================================
	
	public void updateTimestamp() {
		this.timestamp = LocalDateTime.now();
	}
	
	public String getFormattedTimestamp() {
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MMM d, yyyy 'at' h:mm a");
		return timestamp.format(timeFormat);
	}
	
	@Override
	public String toString() {
		return title;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
