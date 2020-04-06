package repository.xml_repositories;

import models.BaseEntity;
import models.Book;
import models.validation.Validator;
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
import java.util.stream.IntStream;

abstract public class XMLRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {

    // todo: implement it like file repos

    protected String PATH;
    protected String DEFAULT_NAME;
    protected String DEFAULT_TXT_FILE_EXTENSION = ".xml";

    protected String path;

    public XMLRepository(Validator<T> validator) {
        super(validator);
    }

    /**
     * Call this in your child constructor to get all data from the file.
     */
    private void loadData() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            IntStream.range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> (Element) node)
                    .map(this::createEntityFromElement)
                    .forEach(super::save);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    protected static String getTextFromTagName(Element parentElement, String tagName) {
        return parentElement.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private void saveEntityToXML(T entity) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            Element root = document.getDocumentElement();
            Node node = entityToNode(entity, document);
            root.appendChild(node);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param element
     * @return
     */
    protected abstract T createEntityFromElement(Element element);

    /**
     *
     */
    protected abstract Node entityToNode(T entity, Document document);
}
