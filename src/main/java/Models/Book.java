package Models;

public class Book extends BaseEntity<Long> {
    private Long Id;
    private String title;
    private String author;
    private int price;
    private Long ownerId;
    private boolean available;

    public Book() {
    }

    public Book(Long ID, String title, String author, int price) {
        this.Id = ID;
        this.title = title;
        this.author = author;
        this.price = price;
        this.ownerId = null;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String string =  "Book - ID = " + Id + ", title = " + title + ", author = " + author + ", price = " + price;
        if (!available)
            string += ", owner ID = " + ownerId;
        string += ";";
        return string;
    }

    @Override
    public Long getId() {
        return Id;
    }

    @Override
    public void setId(Long id) {
        Id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void registerOwner(Long clientID) {
        ownerId = clientID;
        available = false;
    }

    public boolean isAvailable() {
        return available;
    }

    public void deleteOwner() {
        ownerId = null;
        available = true;
    }
}
