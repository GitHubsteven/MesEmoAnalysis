package com.mea.service;

import com.mea.config.Properties;
import com.mea.domain.MessageBean;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rongbin.xie on 2017/7/12.
 */
public class ReadXML {

    /**
     * 获取messages数据
     *
     * @param messageXmlPath 路径
     * @return
     */
    public static List<MessageBean> getMessages(Path messageXmlPath) {
        String MESSAGE = "message";
        String TEXT = "text";
        String SOURCE = "source";
        String USER_PROFILE = "userProfile";
        String FREQUENCY = "frequency";
        String SRCNUMBER = "srcNumber";
        String DESTINATION = "destination";
        String DESTNUMBER = "destNumber";
        String xmlPath = messageXmlPath.toString();

        List<String> srcNumPathOfMessage = new ArrayList<>(Arrays.asList(SOURCE, SRCNUMBER));

        List<String> destNumPathOfMessage = new ArrayList<>(Arrays.asList(DESTINATION, DESTNUMBER));

        List<String> frePathOfMessage = new ArrayList<>();
        frePathOfMessage.add(SOURCE);
        frePathOfMessage.add(USER_PROFILE);
        frePathOfMessage.add(FREQUENCY);

        List<MessageBean> messages = new ArrayList<>();

        Element element;
        File xmlFile = new File(xmlPath);
        DocumentBuilder documentBuilder;
        DocumentBuilderFactory documentBuilderFactory;
        try {
            // 返回documentBuilderFactory对象
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // 返回db对象用documentBuilderFactory对象获得返回documentBuilder对象
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // 得到一个DOM并返回给document对象
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            // 得到一个element根元素
            element = document.getDocumentElement();
            // 获得根节点
//            System.out.println("根元素：" + element.getNodeName());
            // 获得根元素下的子message子节点
            NodeList messageList = document.getElementsByTagName(MESSAGE);
            for (int i = 0; i < messageList.getLength(); i++) {
                Node message = messageList.item(i);
                MessageBean messageBean = new MessageBean();
                if (message.getNodeType() == Node.ELEMENT_NODE) {
                    Element messageEle = (Element) message;
                    String id = messageEle.getAttribute("id");
                    if (!StringUtils.isEmpty(id)) {
                        messageBean.setId(id);
                    }
                    NodeList textList = messageEle.getElementsByTagName(TEXT);
                    if (textList.getLength() > 0) {
                        String text = textList.item(0).getTextContent();
                        messageBean.setText(text);
                    }
                    Element frequency = findTargetElement(messageEle, frePathOfMessage);
                    if (null != frequency) {
                        String frequencyContext = frequency.getTextContent();
                        Double value = Properties.FREQUENCY_VALUE_MAP.get(frequencyContext.toLowerCase().trim());
                        if (value != null) {
                            messageBean.setFrequency(value);
                        }
                    }

                    Element srcNumberEle = findTargetElement(messageEle, srcNumPathOfMessage);
                    if (null != frequency) {
                        String srcNumber = srcNumberEle.getTextContent();
                        if (!StringUtils.isEmpty(srcNumber)) {
                            messageBean.setSrcNumber(srcNumber);
                        }
                    }
                    Element destNumberEle = findTargetElement(messageEle, destNumPathOfMessage);
                    if (null != frequency) {
                        String destNumber = destNumberEle.getTextContent();
                        if (!StringUtils.isEmpty(destNumber)) {
                            messageBean.setDesNumber(destNumber);
                        }
                    }
                }
                messages.add(messageBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;

    }

    /**
     * 通过根节点，递归找到目标节点
     *
     * @param root              root 节点
     * @param targetElementPath 目标子节点和root借点的路径，不包含root子节点
     * @return 目标节点
     */
    private static Element findTargetElement(Element root, List<String> targetElementPath) {
        /**
         * path size >1 , root.getBy(path.getS(0).getLength > 0) , call self function
         * path size = 1 , root.getBy(path.get(0)).item(0).getNodeName  == path.get(0), return node
         * path size >1, root.getBy(path.getS(0).getLength = 0) return null;
         *
         *path= null || root == null ,return null;
         *
         */
        if (targetElementPath.size() == 0 || null == root) {
            return null;
        }
        NodeList childrenNodes = root.getElementsByTagName(targetElementPath.get(0));
        if (childrenNodes.getLength() == 0 && targetElementPath.size() > 1) {
            return null;
        }
        if (targetElementPath.size() == 1 && targetElementPath.get(0).equals(childrenNodes.item(0).getNodeName())) {
            return (Element) childrenNodes.item(0);
        }
        if (childrenNodes.getLength() > 0 && targetElementPath.size() > 1) {
            if (childrenNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
                Element subRoot = (Element) childrenNodes.item(0);
                targetElementPath.remove(0);
                return findTargetElement(subRoot, targetElementPath);
            }
        }
        return null;
    }
}

