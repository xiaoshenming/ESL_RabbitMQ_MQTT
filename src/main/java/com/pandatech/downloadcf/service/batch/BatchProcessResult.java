package com.pandatech.downloadcf.service.batch;

/**
 * 批量处理结果
 * 封装批量操作的结果信息
 */
public class BatchProcessResult {
    private int totalCount;
    private int successCount;
    private int failureCount;
    private long processingTimeMs;
    private String errorMessage;
    
    public BatchProcessResult() {
        this.processingTimeMs = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public int getTotalCount() { 
        return totalCount; 
    }
    
    public void setTotalCount(int totalCount) { 
        this.totalCount = totalCount; 
    }
    
    public int getSuccessCount() { 
        return successCount; 
    }
    
    public void setSuccessCount(int successCount) { 
        this.successCount = successCount; 
    }
    
    public int getFailureCount() { 
        return failureCount; 
    }
    
    public void setFailureCount(int failureCount) { 
        this.failureCount = failureCount; 
    }
    
    public long getProcessingTimeMs() { 
        return processingTimeMs; 
    }
    
    public void setProcessingTimeMs(long processingTimeMs) { 
        this.processingTimeMs = processingTimeMs; 
    }
    
    public String getErrorMessage() { 
        return errorMessage; 
    }
    
    public void setErrorMessage(String errorMessage) { 
        this.errorMessage = errorMessage; 
    }
    
    /**
     * 计算成功率
     */
    public double getSuccessRate() {
        return totalCount > 0 ? (double) successCount / totalCount * 100 : 0;
    }
    
    /**
     * 检查是否全部成功
     */
    public boolean isAllSuccess() {
        return totalCount > 0 && successCount == totalCount;
    }
    
    /**
     * 检查是否有失败
     */
    public boolean hasFailures() {
        return failureCount > 0;
    }
    
    /**
     * 完成处理，记录结束时间
     */
    public void markCompleted() {
        this.processingTimeMs = System.currentTimeMillis() - this.processingTimeMs;
    }
    
    @Override
    public String toString() {
        return String.format("BatchProcessResult{总数=%d, 成功=%d, 失败=%d, 成功率=%.2f%%, 处理时间=%dms}", 
                totalCount, successCount, failureCount, getSuccessRate(), processingTimeMs);
    }
}