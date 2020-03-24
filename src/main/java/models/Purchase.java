package models;

import java.time.LocalDateTime;

public class Purchase extends BaseEntity<String> {
    Long bookId;
    Long clientId;
    LocalDateTime lastModifiedDateTime;

    public Purchase(Long bookId, Long clientId, LocalDateTime lastModifiedDateTime) {
        this.bookId = bookId;
        this.clientId = clientId;
        this.id = bookId + "-" + clientId;
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public Purchase(Long bookId, Long clientId) {
        this.bookId = bookId;
        this.clientId = clientId;
        this.id = bookId + "-" + clientId;
        updateLastModified();
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
        updateLastModified();
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
        updateLastModified();
    }

    public LocalDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    private void updateLastModified() {
        this.lastModifiedDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Purchase: " + "id: " + id + ", bookId: " + bookId + ", clientId: " + clientId + ", last modified at: " + lastModifiedDateTime;
    }
}