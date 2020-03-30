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
        updateLastModifiedWithCurrentData();
    }

    public Purchase(Long bookId, Long clientId, LocalDate lastModifiedDate) {
        this.bookId = bookId;
        this.clientId = clientId;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
        updateLastModifiedWithCurrentData();
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
        updateLastModifiedWithCurrentData();
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    private void updateLastModifiedWithCurrentData() {
        this.lastModifiedDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Purchase: " + "id: " + id + ", bookId: " + bookId + ", clientId: " + clientId + ", last modified in: " + lastModifiedDate;
    }
}
