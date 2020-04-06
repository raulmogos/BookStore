package repository.xml_repositories;

import models.Book;
import models.validation.Validator;
import models.validation.ValidatorException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository.InMemoryRepository;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

public class BookXMLRepository extends InMemoryRepository<Long, Book> {

    private static final String PATH = "data/xml/book/";
    private static final String DEFAULT_NAME = "books";
    private static final String DEFAULT_FILE_EXTENSION = ".xml";

    private String path;

    public BookXMLRepository(Validator<Book> validator) {
        super(validator);
        this.path = PATH + DEFAULT_NAME + DEFAULT_FILE_EXTENSION;
        this.loadData();
    }

    public BookXMLRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.path = PATH + fileName + DEFAULT_FILE_EXTENSION;
        this.loadData();
    }

    private void loadData() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            IntStream.range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> (Element) node)
                    .map(this::createBookFromElement)
                    .forEach(super::save);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        return parentElement.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private Book createBookFromElement(Element bookElement) {
        return new Book(
                Long.parseLong(bookElement.getAttribute("id")),
                getTextFromTagName(bookElement, "title"),
                getTextFromTagName(bookElement, "author"),
                Integer.parseInt(getTextFromTagName(bookElement, "price"))
        );
    }

    private void saveBookToXML(Book book) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            Node bookNode = bookToNode(book, document);
            root.appendChild(bookNode);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void deleteNodeFromXML(Book book) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            Element bookElement = IntStream.range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> (Element) node)
                    .filter(nodeElement -> Integer.parseInt(nodeElement.getAttribute("id")) == book.getId())
                    .findFirst().get();
            root.removeChild(bookElement);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Node bookToNode(Book book, Document document) {
        Element bookElement = document.createElement("book");
        bookElement.setAttribute("id", book.getId().toString());
        appendChildWithTextToNode(document, bookElement, "title", book.getTitle());
        appendChildWithTextToNode(document, bookElement, "author", book.getAuthor());
        appendChildWithTextToNode(document, bookElement, "price", String.valueOf(book.getPrice()));
        return bookElement;
    }

    private static void appendChildWithTextToNode(Document document, Node parentNode, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    @Override
    public Optional<Book> save(Book book) {
        Optional<Book> optionalBook = super.save(book);
        if (optionalBook.isPresent()) {
            return optionalBook;
        }
        saveBookToXML(book);
        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long id) {
        Optional<Book> optionalBook = super.delete(id);
        optionalBook.ifPresent(this::deleteNodeFromXML);
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book book) {
        Optional<Book> optionalBook = super.update(book);
        optionalBook.ifPresent(this::deleteNodeFromXML);
        saveBookToXML(book);
        return Optional.empty();
    }
}
