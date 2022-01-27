package com.board.vo;

public class PagingVO {
	
	// 페이지 10개씩 출력
	private int pageNum;		// 현재 페이지
    private int amount;			// 한 페이지 당 보여질 게시물 개수
    private int skip;			// 스킵할 게시물 수((pageNum-1) * amount )
    
    // 페이지 번호
    private int startPage;		// 화면 시작 페이지 번호
    private int endPage;		// 화면 마지막 페이지 번호
    private int totalCnt;		// 총 페이지 수
    private boolean prev, next;	// 이전 페이지, 다음 페이지 존재 유무
    private int total;			// 전체 게시물 수
    private PagingVO pvo;		// 현재 페이지, 페이지당 게시물 표시 개수 정보
    
    // 검색 
    private String keyword;		// 검색 키워드
    
    public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	// 기본 생성자 -> 기본 세팅 : pageNum = 1, amount = 10
    public PagingVO() {		
        this(1, 10);
        this.skip = 0;
    }
    
    // Pag생성자 => 원하는 pageNum, 원하는 amount
    public PagingVO(int pageNum, int amount) {	
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum-1)*amount;
    }
    
    public int getPageNum() {
		return pageNum;
	}
    
    public void setPageNum(int pageNum) {
    	this.skip = (pageNum-1)*this.amount;
    	
    	this.pageNum = pageNum;
    }

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.skip = (this.pageNum-1)*amount;
		
		this.amount = amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	// 생성자
	public PagingVO(PagingVO pvo, int total) {
		
		this.pvo = pvo;
		this.total = total;
		
		// 마지막 페이지
		this.endPage = (int) Math.ceil(pvo.getPageNum()/10.0) * 10;
		// 시작 페이지
		this.startPage = this.endPage - 9;
		
		// 전체 마지막 페이지
		int totalEnd = (int) (Math.ceil(total * 1.0/pvo.getAmount()));
		
		// totalEnd가 화면에 보이는 endPage 보다 작은 경우, endPage 값 조정
		if (totalEnd < this.endPage) {
			this.endPage = totalEnd;
		}
		
		// prev : startPage 값이 1이 아닌 경우(1 보다 큰 경우) true
		/* this.prev = this.startPage == 1 ? false : true; */
		 this.prev = this.startPage > 1; 
		
		// next : endPage 값이 1보다 큰 경우 true
		/* this.next = this.endPage * pvo.amount < total ? true : false; */ 
		 this.next = this.endPage < totalEnd; 
		
	}
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public PagingVO getPvo() {
		return pvo;
	}

	public void setPvo(PagingVO pvo) {
		this.pvo = pvo;
	}


	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	

	@Override
	public String toString() {
		return "PagingVO [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", totalCnt=" + totalCnt + ", prev=" + prev + ", next=" + next + ", total="
				+ total + ", pvo=" + pvo + ", keyword=" + keyword + "]";
	}
}