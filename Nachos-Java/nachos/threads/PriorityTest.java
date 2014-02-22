package nachos.threads;

public class PriorityTest implements Runnable {
	int priority;
	
	PriorityTest(int priority){
		this.priority = priority;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < this.priority; i++){
			ThreadedKernel.scheduler.increasePriority();
		}
		System.out.println("ending thread ");
	}
}
