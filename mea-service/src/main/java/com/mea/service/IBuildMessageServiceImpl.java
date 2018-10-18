package com.mea.service;

import com.mea.api.TextPreProcess;
import com.google.common.collect.Lists;
import com.mea.config.Configuration;
import com.mea.config.Properties;
import com.mea.domain.DealParticipleResultBean;
import com.mea.domain.MessageBean;
import com.mea.domain.TypeBean;
import com.mea.domain.WordLevel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.mea.utils.SettingUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IBuildMessageServiceImpl implements IBuildMessageService {

    @Override
    public List<MessageBean> mergeTypeToTargetMessage(Path contextPath, Path typePath) {
        // TODO Auto-generated method stub
        List<TypeBean> sampleTypeList = getTypes(typePath);
        List<MessageBean> messages = buildTargetMessage(contextPath);

        messages.forEach(message -> {
            Optional<TypeBean> findFirstType = sampleTypeList.stream().filter(sampleType -> {
                return message.getId().equals(sampleType.getId());
            }).findFirst();
            findFirstType.ifPresent(typeBean -> message.setType(typeBean.getType()));
        });
        return messages;
    }

    @Override
    public List<MessageBean> buildTargetMessage(Path contextPath) {

        List<MessageBean> messages = ReadXML.getMessages(contextPath);
        List<List<MessageBean>> partitions = Lists.partition(messages, Properties.PARTICIPLE_SIZE);
        List<DealParticipleResultBean> partDealResultList = new ArrayList<>();
        partitions.forEach(partition -> {
            String partitionContext = participleMessageContext(partition);
            if (StringUtils.isNotBlank(partitionContext)) {
                List<DealParticipleResultBean> partDealBeanList = getMessageDeatail(partitionContext);
                if (!partDealBeanList.isEmpty()) {
                    partDealResultList.addAll(partDealBeanList);
                }
            }
        });

        List<MessageBean> subList = messages.subList(0, Math.min(messages.size(), partDealResultList.size()));

        subList.forEach(message -> {
            Optional<DealParticipleResultBean> findFirst = partDealResultList.stream().filter(dealResultBean -> {
                return message.getId().equals(dealResultBean.getId().replaceAll("[^0-9]", ""));
            }).findFirst();
            if (findFirst.isPresent()) {
                DealParticipleResultBean dealParticipleResultBean = findFirst.get();
                message.setPositive(dealParticipleResultBean.getPositive()).setNegative(dealParticipleResultBean.getNegative());
            }
        });
        return subList;
    }

    @Override
    public List<String> getPositiveWords(Path filePath) {
        String regex = "\\s+";
        List<String> positiveWords = null;
        try {
            positiveWords = SettingUtils.getWordsFromFile(filePath, regex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return positiveWords;
    }

    @Override
    public List<String> getNegativeWords(Path filePath) {

        String regex = "\\s+";
        List<String> negativeWords = null;
        try {
            negativeWords = SettingUtils.getWordsFromFile(filePath, regex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return negativeWords;
    }

    @Override
    public List<MessageBean> getSampleMessages(Path filePath) {
        return ReadXML.getMessages(filePath);
    }

    @Override
    public List<MessageBean> getTestMessages(Path filePath) {
        return ReadXML.getMessages(filePath);
    }

    @Override
    public List<TypeBean> getTypes(Path filePath) {
        return SettingUtils.getTypeByPath(filePath);
    }

    @Override
    public String participleMessageContext(List<MessageBean> messages) {
        StringBuilder context = new StringBuilder();
        if (messages.isEmpty()) {
            return null;
        }
        messages.forEach(bean -> {
            context.append(bean.getId())
                    .append(Properties.Separator.BLANK)
                    .append(bean.getText())
                    .append(Properties.Separator.HASHTAG);
        });
        String participleResult = null;
        try {
            participleResult = TextPreProcess.participleSentence(context.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return participleResult;
    }

    @Override
    public List<DealParticipleResultBean> getMessageDeatail(String participleResult) {
        List<String> dividedMessages = Arrays.asList(StringUtils.split(participleResult, Properties.Separator.HASHTAG));
        if (CollectionUtils.isEmpty(dividedMessages)) {
            return null;
        }
        List<String> postiveWords = Configuration.loadPositiveConfig();
        List<String> negativeWords = Configuration.loadNegativeConfig();
        List<DealParticipleResultBean> participledMessages = new ArrayList<>();
        dividedMessages.forEach(dividedMessage -> {
            DealParticipleResultBean bean = new DealParticipleResultBean();
            String[] wordsOfMessage = StringUtils.split(dividedMessage, Properties.Separator.BLANK);
            List<String> words = new ArrayList<>(Arrays.asList(wordsOfMessage));
            if (CollectionUtils.isNotEmpty(words)) {
                bean.setId(words.get(0).trim());
                //delete the id, only to contain the text words
                words.remove(0);
                bean.setWords(words);
                if (CollectionUtils.isNotEmpty(words)) {
                    words.forEach(word -> {
                        if (postiveWords.contains(word.trim())) {
                            bean.setPositive(bean.getPositive() + 1);
                        } else if (negativeWords.contains(word.trim())) {
                            bean.setNegative(bean.getNegative() + 1);
                        }
                    });
                }
            }

            participledMessages.add(bean);
        });

        return participledMessages;
    }

    /**
     * 获取短信的情感特征E
     *
     * @param messageUseFulWords 短信有意义词汇
     * @param postiveWords       正面情感词库
     * @param negativeWords      负面情感词库
     * @param levelWords         程度情感词库及值
     * @return 情感特征
     */
    @Override
    public Integer getEmotionCharacter(List<String> messageUseFulWords, List<String> postiveWords, List<String> negativeWords,
                                       List<WordLevel> levelWords) {
        //设置默认值
        Integer E = 0;

        if (null == messageUseFulWords || messageUseFulWords.size() == 0) {
            return E;
        }
        for (int index = 0; index < messageUseFulWords.size(); index++) {
            String word = messageUseFulWords.get(index);
            Integer level = 1;
            //判断是否为积极词汇
            if (postiveWords.contains(word)) {
                if (index - 1 > 0) {
//					获取程度级别
                    String levelWord = messageUseFulWords.get(index - 1);
                    Optional<WordLevel> levelValue = levelWords.stream().filter(levWord -> levWord.getKey().equals(levelWord)).findFirst();
                    if (levelValue.isPresent()) {
                        level = levelValue.get().getValue();
                    }
                }
                E += level;
            } else if (negativeWords.contains(word)) {
                if (index - 1 > 0) {
                    String levelWord = messageUseFulWords.get(index - 1);
                    Optional<WordLevel> levelValue = levelWords.stream().filter(levWord -> levWord.getKey().equals(levelWord)).findFirst();
                    if (levelValue.isPresent()) {
                        level = levelValue.get().getValue();
                    }
                }
                E += level * (-1);
            }
            //如果中性词，那么继续下个循环
        }

        return E;
    }


}
