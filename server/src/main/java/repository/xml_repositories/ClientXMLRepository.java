package repository.xml_repositories;


import models.Client;
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

public class ClientXMLRepository extends InMemoryRepository<Long, Client> {

    private static final String PATH = "data/xml/client/";
    private static final String DEFAULT_NAME = "clients";
    private static final String DEFAULT_FILE_EXTENSION = ".xml";

    private String path;

    public ClientXMLRepository(Validator<Client> validator) {
        super(validator);
        this.path = PATH + DEFAULT_NAME + DEFAULT_FILE_EXTENSION;
        this.loadData();
    }

    public ClientXMLRepository(Validator<Client> validator, String fileName) {
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
                    .map(this::createClientFromElement)
                    .forEach(super::save);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        return parentElement.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private Client createClientFromElement(Element element) {
        return new Client(
                Long.parseLong(element.getAttribute("id")),
                getTextFromTagName(element, "firstName"),
                getTextFromTagName(element, "lastName"),
                Integer.parseInt(getTextFromTagName(element, "moneySpent"))
        );
    }

    private void saveClientToXML(Client client) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            Node clientNode = ClientToNode(client, document);
            root.appendChild(clientNode);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void deleteNodeFromXML(Client client) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            Element clientElement = IntStream.range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> (Element) node)
                    .filter(nodeElement -> Integer.parseInt(nodeElement.getAttribute("id")) == client.getId())
                    .findFirst().get();
            root.removeChild(clientElement);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Node ClientToNode(Client client, Document document) {
        Element element = document.createElement("client");
        element.setAttribute("id", client.getId().toString());
        appendChildWithTextToNode(document, element, "firstName", client.getFirstName());
        appendChildWithTextToNode(document, element, "lastName", client.getLastName());
        appendChildWithTextToNode(document, element, "moneySpent", String.valueOf(client.getMoneySpent()));
        return element;
    }

    private static void appendChildWithTextToNode(Document document, Node parentNode, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    @Override
    public Optional<Client> save(Client client) throws ValidatorException {
        Optional<Client> optionalClient = super.save(client);
        if (optionalClient.isPresent()) {
            return optionalClient;
        }
        saveClientToXML(client);
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long id) {
        Optional<Client> optionalClient = super.delete(id);
        optionalClient.ifPresent(this::deleteNodeFromXML);
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client client) throws ValidatorException {
        Optional<Client> optionalClient = super.update(client);
        optionalClient.ifPresent(this::deleteNodeFromXML);
        saveClientToXML(client);
        return Optional.empty();
    }
}
