package base.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import base.bean.ContentSimilarBean;
import base.bean.DownloadRecordBean;
import base.bean.MobiBean;
import base.bean.ResponseData;
import base.dao.IRecommandDAO;
import base.util.Constent;
import base.util.StringUtil;

@Component
public class RecommendService {

	@Autowired
	private IRecommandDAO mRecommandDAO;
	
	private Map<String,Map<String,Integer>> mContentMap=new HashMap<String, Map<String,Integer>>();
	
	private Set<String> mUserSet=new HashSet<String>();
	
	private List<ContentSimilarBean> mSimilarList=new ArrayList<ContentSimilarBean>();
	
	private static final int DOWNLOAD=1;
	
	private static final int NOT_DOWNLOAD=0;
	
	/**
	 * 记录下载
	 * @param content 书本名
	 * @param user 当前用户
	 */
	public void recordDowload(String content,String user){
		//TODO 记录下载
		Date currentTime=new Date(System.currentTimeMillis());
		mRecommandDAO.recordDowload(content, user, currentTime);
		//generateRecommend();
	}
	
	
	/**
	 * 生成推荐
	 */
	@Scheduled(initialDelay=15*1000,fixedDelay=1000*10*30)
	public void generateRecommend(){
    	resetDataBase();
		clearContainer();
		setContentData();
		fillAllUserToItem();
	//	showMap();
		caculateContentSimilar();
		recordSimilar();
	}
	
	
	/**
	 * 重置数据库
	 */
	public void resetDataBase(){
		mRecommandDAO.resetContentSimilarDB();
	}
	
	
	/**
	 * 通过用户名返回推荐内容
	 * 
	 * @param userName
	 */
	public ResponseData<List<ContentSimilarBean>> getRecommendContentByUser(String userName) {
		if (StringUtil.isEmpty(userName)) {
			userName = Constent.DEFAULT_RECOMMAND_USER;
		}
		List<ContentSimilarBean> contentSimilarList = mRecommandDAO.getRecommendList(userName);
		ResponseData<List<ContentSimilarBean>> data=setResponseData(contentSimilarList);
		return data;
	}
	
	/**根据列表长度，设置反馈信息
	 * @param list
	 * @return
	 */
	public ResponseData<List<ContentSimilarBean>> setResponseData(List<ContentSimilarBean> list){
		ResponseData<List<ContentSimilarBean>> data=new ResponseData<List<ContentSimilarBean>>();
		if(list.size()<=0){
			data.setResultCode(Constent.FAIL_CODE);	
		}else{
			data.setResultCode(Constent.SUCCESS_CODE);
			data.setData(list);
		}
		return data;
	}
	
	/**
	 * 从数据库获取内容，并存储到相应数据结构
	 */
	public void setContentData(){
		List<DownloadRecordBean> contentList=mRecommandDAO.getRecords();
		for(DownloadRecordBean temp:contentList){
			mUserSet.add(temp.getUserName());
			if(mContentMap.containsKey(temp.getContent())){
				//非第一次存
				Map<String, Integer> userMap=mContentMap.get(temp.getContent());
				userMap.put(temp.getUserName(), DOWNLOAD);
			}else{
				//第一次存content
				Map<String, Integer> userMap=new HashMap<String, Integer>();
				userMap.put(temp.getUserName(),DOWNLOAD);
				mContentMap.put(temp.getContent(), userMap);
			}
		}
	}
	
	/**
	 * 对所有书籍item，填充所有用户
	 */
	private void fillAllUserToItem(){
	  Iterator<String> userIterator=mUserSet.iterator();
	  System.out.println("mContentMap的大小是: "+mContentMap.size());
	  while(userIterator.hasNext()){
		  String userName=userIterator.next();
		  Set<String> contentSet= mContentMap.keySet();
		  Iterator<String> contentIterator=contentSet.iterator();
		  while(contentIterator.hasNext()){
			  String contentName=contentIterator.next();
			  Map<String, Integer> userItemMap=mContentMap.get(contentName);
			  if(!userItemMap.containsKey(userName)){
				  userItemMap.put(userName, NOT_DOWNLOAD);
			  }
		  }
	  }
	}
	
	/**
	 * 显示map数据
	 */
	private void showMap(){
	 Set<String> contentSet=mContentMap.keySet();
	 Iterator<String> iterator=contentSet.iterator();
	 while(iterator.hasNext()){
		 String key=iterator.next();
		 System.out.println("content="+key);
		 Map<String, Integer> map=mContentMap.get(key);
		 Iterator<String> iterator2=map.keySet().iterator();
		 while(iterator2.hasNext()){
              String nameString=iterator2.next();
              int value=map.get(nameString);
              System.out.println(" user="+nameString +" value="+value);
		 }
	 }
	}

	/**
	 * 清除数据结构
	 */
	private void clearContainer(){
		mSimilarList.clear();
		mContentMap.clear();
		mUserSet.clear();
	}
	
	/**
	 * 计算所有的书籍的相似度
	 */
	private void caculateContentSimilar(){
		List<String> contentList = new ArrayList<String>(mContentMap.keySet());
		int length = contentList.size();
		for (int i = 0; i < length-1; i++) {
			String contentSource = contentList.get(i);
			for (int j = i + 1; j < length; j++) {
				String contentDest = contentList.get(j);
				double similar=cacluete(mContentMap.get(contentSource),mContentMap.get(contentDest));
				ContentSimilarBean tempBean=new ContentSimilarBean(contentSource,contentDest,similar);
				mSimilarList.add(tempBean);
			}
		}
	}
	
	/**
	 * 计算两个书籍之间的相似度
	 * @param contentSourceMap
	 * @param contentDestMap
	 * @return
	 */
	private double cacluete(Map<String,Integer>contentSourceMap,Map<String,Integer> contentDestMap){
		Iterator<String> userIterator=contentSourceMap.keySet().iterator();
		double sum=0;
		while(userIterator.hasNext()){
			String userName=userIterator.next();
			int contentSourceValue=contentSourceMap.get(userName);
			int contentDestValue=contentDestMap.get(userName);
			sum+=Math.pow(contentSourceValue-contentDestValue, 2);
		}
		double temp=1.0/(1.0+Math.sqrt(sum));
		BigDecimal   bigDecimal   =   new   BigDecimal(temp);  
		double similarValue = bigDecimal.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();  
		return similarValue;
	}
	
	/**
	 * 记录相似度的值到数据库
	 */
	public void recordSimilar(){
		mRecommandDAO.recordSimlar(mSimilarList);
	}

	/**获得热门推荐内容
	 * @return
	 */
	public ResponseData<List<MobiBean>> getHotContent(){
		List<MobiBean> list=mRecommandDAO.getHotContent();
		ResponseData<List<MobiBean>> resp=new ResponseData<List<MobiBean>>();
		if(list.size()<=0){
			resp.setResultCode(Constent.FAIL_CODE);
		}else{
			resp.setData(list);
			resp.setResultCode(Constent.SUCCESS_CODE);
		}
		return resp;
	}
	
	/**获得热门推荐内容
	 * @return
	 */
	public ResponseData<List<MobiBean>> getRandomContent(){
		List<MobiBean> list=mRecommandDAO.getRandomContent();
		ResponseData<List<MobiBean>> resp=new ResponseData<List<MobiBean>>();
		if(list.size()<=0){
			resp.setResultCode(Constent.FAIL_CODE);
		}else{
			resp.setData(list);
			resp.setResultCode(Constent.SUCCESS_CODE);
		}
		return resp;
	}
}
