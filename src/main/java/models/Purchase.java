package models;

import java.time.LocalDate;

public class Purchase extends BaseEntity<Long> {
    Long bookId;
    Long clientId;
    LocalDate lastModifiedDate;

    public Purchase(Long id, Long bookId, Long clientId, LocalDate lastModifiedDateTime) {
        this.id = id;
        this.bookId = bookId;
        this.clientId = clientId;
        this.lastModifiedDate = lastModifiedDateTime;
    }

    public Purchase (Long id, Long bookId, Long clientId) {
        this.id = id;
        this.bookId = bookId;
        this.clientId = clientId;
    }

    public Purchase(Long bookId, Long clientId, LocalDate lastModifiedDateTime) {
        this.bookId = bookId;
        this.clientId = clientId;
        this.lastModifiedDate = lastModifiedDateTime;
    }

    public Purchase(Long bookId, Long clientId) {
        this.bookId = bookId;
        this.clientId = clientId;
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

    public LocalDate getLastModifiedDateTime() {
        return lastModifiedDate;
    }

    public void setLastModifiedDateTime(LocalDate lastModifiedDateTime) {
        this.lastModifiedDate = lastModifiedDateTime;
    }

    private void updateLastModified() {
        this.lastModifiedDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Purchase: " + "id: " + id + ", bookId: " + bookId + ", clientId: " + clientId + ", last modified at: " + lastModifiedDate;
    }
}
