/**
 * 
 */
package nachos.threads;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nachos.machine.Config;
import nachos.machine.Lib;
import nachos.machine.Machine;

/**
 * @author jacob
 *
 */
public class StaticPriorityScheduler extends Scheduler{

	
	int maxPriority = Integer.parseInt(Config.getString("scheduler.maxPriorityValue"));
	// Just map the thread to its priority
	public HashMap<KThread, Integer> threadPriorityMap = new HashMap<KThread, Integer>();
	
	public int getPriority(KThread kthread){
		if(this.threadPriorityMap.containsKey(kthread)){
			return this.threadPriorityMap.get(kthread);
		}else{
			return -1;
		}
	}
	
	public int getPriority(){
		return this.getPriority(KThread.currentThread());
	}
	
	public int getEffectivePriority(KThread kthread){
		Lib.assertTrue(Machine.interrupt().disabled());
		int currentThreadPriority = this.threadPriorityMap.get(kthread);
		int cumulativePriority = 0;

		Iterator<KThread> kthreadIterator = this.threadPriorityMap.keySet().iterator();
		KThread currentKThread = null;
		while(kthreadIterator.hasNext()){
			cumulativePriority += this.threadPriorityMap.get(kthreadIterator.next());
		}
		
		return (cumulativePriority > currentThreadPriority) ? cumulativePriority : currentThreadPriority;
	}
	
	public int getEffectivePriority(){
		return this.getEffectivePriority(KThread.currentThread());
	}
	
	public void setPriority(KThread kthread, int priority){
		if(this.threadPriorityMap.containsKey(kthread)){
			this.threadPriorityMap.remove(kthread);
		}
		
		this.threadPriorityMap.put(kthread, priority);
	}
	
	public void setPriority(int priority){
		if(this.threadPriorityMap.containsKey(KThread.currentThread())){
			this.threadPriorityMap.remove(KThread.currentThread());
		}
		
		this.threadPriorityMap.put(KThread.currentThread(), priority);
	}
	
	public boolean increasePriority(){
		if(this.threadPriorityMap.containsKey(KThread.currentThread()) && this.threadPriorityMap.get(KThread.currentThread()) < this.maxPriority){
			int currentPriority = this.threadPriorityMap.remove(KThread.currentThread());
			this.threadPriorityMap.put(KThread.currentThread(), currentPriority++);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public PriorityThreadQueue newThreadQueue(boolean transferPriority) {
		// TODO Auto-generated method stub
		return new PriorityThreadQueue();
	}
	
	public class PriorityThreadQueue extends ThreadQueue{

		@Override
		public void waitForAccess(nachos.threads.KThread thread) {
			// TODO Auto-generated method stub
		}

		@Override
		public nachos.threads.KThread nextThread() {
			// TODO Auto-generated method stub
			Iterator<KThread> iterator = threadPriorityMap.keySet().iterator();
			KThread currentKThread = null;
			KThread highestKThread = KThread.currentThread();
			while(iterator.hasNext()){
				currentKThread = iterator.next();
				highestKThread = (threadPriorityMap.get(currentKThread) > threadPriorityMap.get(highestKThread)) ? currentKThread : highestKThread;
			}
			return highestKThread;
		}

		@Override
		public void acquire(nachos.threads.KThread thread) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void print() {
			// TODO Auto-generated method stub
			
		}
	}
}
