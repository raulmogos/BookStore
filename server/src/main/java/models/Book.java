package models;

public class Book extends BaseEntity<Long> {
    private String title;
    private String author;
    private int price;

    public Book(Long ID, String title, String author, int price) {
        this.id = ID;
        this.title = title;
        this.author = author;
        this.price = price;
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
        return "Book - ID = " + id + ", title = " + title + ", author = " + author + ", price = " + price + ";";
    }
}
