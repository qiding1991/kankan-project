package com.kankan;

class CustomQueue {

  int[] inQueue;
  int inIndex = 0;
  int[] outQueue;
  int outIndex = 0;
  boolean status = true; //true 当前是写状态 false 当前是out状态

  public CustomQueue(int length) {
    inQueue = new int[length];
    outQueue = new int[length];
  }


  public void push(int item) {
     //非写状态，切换到写状态
    if(!status){
       swapStatus();
    }
    if (inIndex + 1 >= inQueue.length) {
      throw new IndexOutOfBoundsException();
    }
    inQueue[inIndex] = item;
  }

  public int pop() {
     if(status){
        swapStatus();
     }
     if(outIndex ==0){
        throw new IndexOutOfBoundsException();
     }
     int result =  outQueue[outIndex];
     outIndex  = outIndex -1;;
     return result;
  }

  public void swapStatus() {
    //当前是写状态
    if (status) {
      for(int i =inIndex;i>=0;inIndex--,outIndex++){
        outQueue[outIndex] = inQueue[inIndex];
      }
      inIndex=0;
      return;
    }
    for(int i =outIndex;i>=0;outIndex--,inIndex++){
      inQueue[inIndex] = outQueue[outIndex];
    }
    outIndex =0;
  }
}


public class QidingQueue {


  public static void main(String[] args) {

  }

}