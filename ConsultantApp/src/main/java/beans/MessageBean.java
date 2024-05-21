package beans;

import java.io.Serializable;
import java.util.Date;

public class MessageBean implements Serializable {
	
	@Override
	public String toString() {
		return "MessageBean [content=" + content + ", subject=" + subject + ", id=" + id + ", senderId=" + senderId
				+ ", recieverId=" + recieverId + ", timestamp=" + timestamp + ", read=" + read + "]";
	}


	private String content, subject;
	private int id,senderId, recieverId;
	private Date timestamp;
	private boolean read;
	

	public MessageBean(String content, int id, int senderId, int revieverId, Date timestamp, String subject, boolean read) {
		super();
		this.content = content;
		this.id = id;
		this.senderId = senderId;
		this.recieverId = revieverId;
		this.timestamp = timestamp;
		this.subject = subject;
		this.read = read;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getSenderId() {
		return senderId;
	}


	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}


	public int getRecieverId() {
		return recieverId;
	}


	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public MessageBean() {
		// TODO Auto-generated constructor stub
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public boolean isRead() {
		return read;
	}


	public void setRead(boolean read) {
		this.read = read;
	}

}
