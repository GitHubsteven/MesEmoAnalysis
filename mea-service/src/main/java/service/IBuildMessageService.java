/**
 * 
 */
package service;

import java.nio.file.Path;
import java.util.List;

import domain.DealParticipleResultBean;
import domain.MessageBean;
import domain.TypeBean;
import domain.WordLevel;

/**
 * @author dell
 *
 */
public interface IBuildMessageService {
	
	/**
	 * 将message的类型合并到message中
	 * 
	 * @param contextPath 内容路径
	 * @param typePath 类型路径
	 * @return
	 */
	public List<MessageBean> mergeTypeToTargetMessage(Path contextPath,Path typePath);
	
	/**
	 * 获取得到三个特征量的函数,三个特征量的函数分别是positive, negative, frequency
	 * 
	 * @param contextPath 内容路径
	 * @param typePath 类型路径
	 * @return List<MessageBean>
	 */
	public List<MessageBean> buildTargetMessage(Path contextPath);
	
	/**
	 * 获取正面情绪词汇
	 * 
	 * @param filePath 文件路径
	 * @return List<String>
	 */
	public List<String> getPositiveWords(Path filePath);
	
	/**
	 * 获取负面情绪词汇
	 * 
	 * @param filePath 文件路径
	 * @return  List<String>
	 */
	public List<String> getNegativeWords(Path filePath);
	
	/**
	 * 获取样本message的相关信息
	 * 
	 * @param filePath 文件路径
	 * @return List<MessageBean>
	 */
	public List<MessageBean> getSampleMessages(Path filePath);
	
	/**
	 * 获取测试message的相关信息
	 * @param filePath 测试message xml文件路径
	 * @return
	 */
	public List<MessageBean> getTestMessages(Path filePath);
	
	/**
	 * 根据类型文件获取类型
	 * 
	 * @param filePath
	 * @return List<TypeBean>
	 */
	public List<TypeBean> getTypes(Path filePath);
	
	
	/**
	 * 分词
	 * 
	 * @param context
	 * @return
	 */
	public String participleMessageContext(List<MessageBean> messages);
	
	/**
	 * 处理得到的分词结果
	 * 
	 * @param participleResult
	 * @return List<DealParticipleResultBean>
	 */
	public List<DealParticipleResultBean> getMessageDeatail(String participleResult);
	
	/**
	 * 获取短信的情感特征E
	 * 
	 * @param messageWords 短信词汇
	 * @param postiveWords 正面情感词库
	 * @param negativeWords 负面情感词库
	 * @param levelWords 程度情感词库及值
	 * @return 情感特征
	 */
	public Integer getEmotionCharacter(List<String> messageWords,List<String> postiveWords, List<String>negativeWords,List<WordLevel> levelWords);
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
