package repository.xml_repositories;

import models.Purchase;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;

public class PurchaseXMLRepository extends InMemoryRepository<Long, Purchase> {

    private static final String PATH = "data/xml/purchase/";
    private static final String DEFAULT_NAME = "purchases";
    private static final String DEFAULT_FILE_EXTENSION = ".xml";

    private String path;

    public PurchaseXMLRepository(Validator<Purchase> validator) {
        super(validator);
        this.path = PATH + DEFAULT_NAME + DEFAULT_FILE_EXTENSION;
        this.loadData();
    }

    public PurchaseXMLRepository(Validator<Purchase> validator, String fileName) {
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
                    .map(this::createPurchaseFromElement)
                    .forEach(super::save);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        return parentElement.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private Purchase createPurchaseFromElement(Element element) {
        return new Purchase(
                Long.parseLong(element.getAttribute("id")),
                Long.parseLong(getTextFromTagName(element, "bookId")),
                Long.parseLong(getTextFromTagName(element, "clientId")),
                LocalDate.parse(getTextFromTagName(element, "date"))
        );
    }

    private void savePurchaseToXML(Purchase purchase) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            Node purchaseNode = PurchaseToNode(purchase, document);
            root.appendChild(purchaseNode);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void deleteNodeFromXML(Purchase purchase) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            Element purchaseElement = IntStream.range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> (Element) node)
                    .filter(nodeElement -> Long.parseLong(nodeElement.getAttribute("id")) == purchase.getId())
                    .findFirst().get();
            root.removeChild(purchaseElement);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Node PurchaseToNode(Purchase purchase, Document document) {
        Element element = document.createElement("purchase");
        element.setAttribute("id", purchase.getId().toString());
        appendChildWithTextToNode(document, element, "bookId", purchase.getBookId().toString());
        appendChildWithTextToNode(document, element, "clientId", purchase.getClientId().toString());
        appendChildWithTextToNode(document, element, "date", String.valueOf(purchase.getLastModifiedDate()));
        return element;
    }

    private static void appendChildWithTextToNode(Document document, Node parentNode, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    @Override
    public Optional<Purchase> save(Purchase purchase) throws ValidatorException {
        Optional<Purchase> optionalPurchase = super.save(purchase);
        if (optionalPurchase.isPresent()) {
            return optionalPurchase;
        }
        savePurchaseToXML(purchase);
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> delete(Long id) {
        Optional<Purchase> optionalPurchase = super.delete(id);
        optionalPurchase.ifPresent(this::deleteNodeFromXML);
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> update(Purchase purchase) throws ValidatorException {
        Optional<Purchase> optionalPurchase = super.update(purchase);
        optionalPurchase.ifPresent(this::deleteNodeFromXML);
        savePurchaseToXML(purchase);
        return Optional.empty();
    }
}
