package bean;

import org.apache.lucene.search.Hits;

/**
 * @author KoreQ
 * @version V1.0
 * @Copyright (c) 2016 
 * @Description class description
 * @date 16-1-9
 */
public class PageBean {
    private String currentPage;
    private int maxPage;
    private String itemNum;
    private Hits hits;
    private int intCurrentPage;
    private int intItemNum;

    public PageBean(String currentPage, String itemNum, Hits hits) {
        this.currentPage = currentPage;
        this.itemNum = itemNum;
        this.hits = hits;
        calculateMaxPage();
    }

    private void calculateMaxPage(){
        try{
            intCurrentPage = Integer.parseInt(currentPage);
            intItemNum = Integer.parseInt(itemNum);
            if(hits.length() % intItemNum > 0){
                maxPage = hits.length() / intItemNum + 1;
            }else if(hits.length() % intItemNum == 0){
                maxPage = hits.length() / intItemNum;
            }
        }catch (Exception e){
            System.err.println("PageBean calculateMaxPage num format error");
        }
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public int getIntCurrentPage() {
        return intCurrentPage;
    }

    public void setIntCurrentPage(int intCurrentPage) {
        this.intCurrentPage = intCurrentPage;
    }

    public int getIntItemNum() {
        return intItemNum;
    }

    public void setIntItemNum(int intItemNum) {
        this.intItemNum = intItemNum;
    }
}
