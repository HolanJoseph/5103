package nachos.threads;

import nachos.machine.*;
import java.util.LinkedList;

/**
 * A scheduler that chooses threads based on their priorities.
 * 
 * <p>
 * A priority scheduler associates a priority with each thread. The next thread
 * to be dequeued is always a thread with priority no less than any other
 * waiting thread's priority. Like a round-robin scheduler, the thread that is
 * dequeued is, among all the threads of the same (highest) priority, the thread
 * that has been waiting longest.
 * 
 * <p>
 * Essentially, a priority scheduler gives access in a round-robin fassion to
 * all the highest-priority threads, and ignores all other threads. This has the
 * potential to starve a thread if there's always a thread waiting with higher
 * priority.
 * 
 * <p>
 * A priority scheduler must partially solve the priority inversion problem; in
 * particular, priority must be donated through locks, and through joins.
 */
public class DynamicPriorityScheduler extends Scheduler {
	/**
	 * Allocate a new priority scheduler.
	 */

	public DynamicPriorityScheduler() {
	}

	/**
	 * Allocate a new priority thread queue.
	 * 
	 * @param transferPriority
	 *            <tt>true</tt> if this queue should transfer priority from
	 *            waiting threads to the owning thread.
	 * @return a new priority thread queue.
	 */
	public ThreadQueue newThreadQueue(boolean transferPriority) {
		return new PriorityQueue(transferPriority);
	}

	public int getPriority(KThread thread) {
		Lib.assertTrue(Machine.interrupt().disabled());

		return getThreadState(thread).getPriority();
	}

	public int getEffectivePriority(KThread thread) {
		Lib.assertTrue(Machine.interrupt().disabled());

		return getThreadState(thread).getEffectivePriority();
	}

	public void setPriority(KThread thread, int priority) {
		Lib.assertTrue(Machine.interrupt().disabled());

<<<<<<< HEAD
		Lib.assertTrue(priority >= maximumPriority && priority <= minimumPriority);
=======
		Lib.assertTrue(priority >= priorityMinimum && priority <= priorityMaximum);
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce

		getThreadState(thread).setPriority(priority);
	}

	public boolean increasePriority() {
		boolean intStatus = Machine.interrupt().disable();

		KThread thread = KThread.currentThread();

		int priority = getPriority(thread);
<<<<<<< HEAD
		if (priority == maximumPriority)
			return false;

		setPriority(thread, priority - 1);
=======

		if (priority == priorityMaximum)
			return false;

		setPriority(thread, priority + 1);
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce

		Machine.interrupt().restore(intStatus);
		return true;
	}

	public boolean decreasePriority() {
		boolean intStatus = Machine.interrupt().disable();

		KThread thread = KThread.currentThread();

		int priority = getPriority(thread);
<<<<<<< HEAD
		if (priority == minimumPriority)
			return false;

		setPriority(thread, priority + 1);
=======
		if (priority == priorityMinimum)
			return false;

		setPriority(thread, priority - 1);
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce

		Machine.interrupt().restore(intStatus);
		return true;
	}

	/**
<<<<<<< HEAD
	 * The minimum priority that a thread can have. Do not change this value.
	 */
	public static final int minimumPriority = Integer.parseInt(Config.getString("scheduler.maxPriorityValue"));;
	/**
	 * The maximum priority that a thread can have. Do not change this value.
	 */
	public static final int maximumPriority = 0;

	/**
	 * The default priority for a new thread. Do not change this value.
	 */
	public static final int priorityDefault = (minimumPriority + maximumPriority) / 2;

	/**
	 * 
	 */
	public static final long agingTime = Long.parseLong(Config.getString("scheduler.agingTime"));
=======
	 * The default priority for a new thread. Do not change this value.
	 */
	public static final int priorityDefault = 1;
	/**
	 * The minimum priority that a thread can have. Do not change this value.
	 */
	public static final int priorityMinimum = 0;
	/**
	 * The maximum priority that a thread can have. Do not change this value.
	 */
	public static final int priorityMaximum = Integer.parseInt(Config.getString("scheduler.maxPriorityValue"));
	public long agingTime = Integer.parseInt(Config.getString("scheduler.agingTime"));
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce

	/**
	 * Return the scheduling state of the specified thread.
	 * 
	 * @param thread
	 *            the thread whose scheduling state to return.
	 * @return the scheduling state of the specified thread.
	 */
	protected ThreadState getThreadState(KThread thread) {
		if (thread.schedulingState == null)
			thread.schedulingState = new ThreadState(thread);

		return (ThreadState) thread.schedulingState;
	}

	/**
	 * A <tt>ThreadQueue</tt> that sorts threads by priority.
	 */
	protected class PriorityQueue extends ThreadQueue {

<<<<<<< HEAD
		private LinkedList<KThread> waitList;

		PriorityQueue(boolean transferPriority) {
			this.transferPriority = transferPriority;
			waitList = new LinkedList<KThread>();
=======
		private LinkedList<KThread> waitList = new LinkedList<KThread>();

		PriorityQueue(boolean transferPriority) {
			this.transferPriority = transferPriority;
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
		}

		public void waitForAccess(KThread thread) {
			Lib.assertTrue(Machine.interrupt().disabled());
			getThreadState(thread).waitForAccess(this);
		}

		public void acquire(KThread thread) {
			Lib.assertTrue(Machine.interrupt().disabled());
			getThreadState(thread).acquire(this);
		}

		public KThread nextThread() {
			Lib.assertTrue(Machine.interrupt().disabled());
<<<<<<< HEAD
			System.out.println("queue size: " + waitList.size());
=======
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
			KThread priorityThread = null;
			for (KThread currentThread : waitList) {
				if (priorityThread == null) {
					priorityThread = currentThread;
				} else if (getPriority(priorityThread) > getPriority(currentThread)) {
					priorityThread = currentThread;
				}
			}
<<<<<<< HEAD
			if (priorityThread != null)
				getThreadState(priorityThread).acquire(this);
			waitList.remove(priorityThread);
			if (priorityThread != null)
				System.out.println("next thread has priority: " + getPriority(priorityThread));
			return priorityThread;
		}

		public int getSize() {
			return waitList.size();
		}

=======
			acquire(priorityThread);
			// if (priorityThread != null)
			// System.out.println("next thread has priority: " +
			// getPriority(priorityThread));
			return priorityThread;
		}

>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
		/**
		 * Return the next thread that <tt>nextThread()</tt> would return,
		 * without modifying the state of this queue.
		 * 
		 * @return the next thread that <tt>nextThread()</tt> would return.
		 */
		protected ThreadState pickNextThread() {
			// implement me
			Lib.assertTrue(Machine.interrupt().disabled());
			KThread priorityThread = null;
			for (KThread currentThread : waitList) {
				if (priorityThread == null) {
					priorityThread = currentThread;
<<<<<<< HEAD
				} else if (getPriority(priorityThread) < getPriority(currentThread)) {
=======
				} else if (getPriority(priorityThread) > getPriority(currentThread)) {
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
					priorityThread = currentThread;
				}
			}
			return (ThreadState) priorityThread.schedulingState;
		}

		public void print() {
			// implement me (if you want)
			Lib.assertTrue(Machine.interrupt().disabled());
			for (KThread currentThread : waitList) {
				System.out.println(currentThread.getName());
			}
		}

		/**
		 * <tt>true</tt> if this queue should transfer priority from waiting
		 * threads to the owning thread.
		 */
		public boolean transferPriority;
	}

	/**
	 * The scheduling state of a thread. This should include the thread's
	 * priority, its effective priority, any objects it owns, and the queue it's
	 * waiting for, if any.
	 * 
	 * @see nachos.threads.KThread#schedulingState
	 */
	protected class ThreadState {
		/**
		 * Allocate a new <tt>ThreadState</tt> object and associate it with the
		 * specified thread.
		 * 
		 * @param thread
		 *            the thread this state belongs to.
		 */
		public ThreadState(KThread thread) {
			this.thread = thread;
<<<<<<< HEAD
			this.priority = -1;
			this.runningSince = -1;
			this.waitingSince = -1;
			this.waitQueue = null;
=======
			this.waitingSince = -1;
			this.runningSince = -1;
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
			setPriority(priorityDefault);
		}

		/**
		 * Return the priority of the associated thread.
		 * 
		 * @return the priority of the associated thread.
		 */
		public int getPriority() {
<<<<<<< HEAD
			if(this.waitingSince < 0)
				return priority;
			
			int waitingFor = (int) ((System.currentTimeMillis() - this.waitingSince) / agingTime);
			this.priority = (int) Math.max(priority - waitingFor, minimumPriority);
				
			return priority;
=======
			this.priority += ((System.currentTimeMillis() - this.waitingSince) / agingTime);
			return (priority = (priority > priorityMaximum) ? priorityMaximum : priority);
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
		}

		/**
		 * Return the effective priority of the associated thread.
		 * 
		 * @return the effective priority of the associated thread.
		 */
		public int getEffectivePriority() {
			// implement me
<<<<<<< HEAD
			return priority;
=======
			int effectivePriority = 0;
			for (KThread thread : this.waitQueue.waitList) {
				if (thread != this.thread)
					effectivePriority += DynamicPriorityScheduler.this.getPriority(thread);
			}
			effectivePriority = (priority > effectivePriority) ? priority : effectivePriority;
			System.out.println("effective priority: " + effectivePriority);
			return (effectivePriority > priorityMaximum) ? priorityMaximum : effectivePriority;
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
		}

		/**
		 * Set the priority of the associated thread to the specified value.
		 * 
		 * @param priority
		 *            the new priority.
		 */
		public void setPriority(int priority) {
			if (this.priority == priority)
				return;

			this.priority = priority;

			// implement me
		}

		/**
		 * Called when <tt>waitForAccess(thread)</tt> (where <tt>thread</tt> is
		 * the associated thread) is invoked on the specified priority queue.
		 * The associated thread is therefore waiting for access to the resource
		 * guarded by <tt>waitQueue</tt>. This method is only called if the
		 * associated thread cannot immediately obtain access.
		 * 
		 * @param waitQueue
		 *            the queue that the associated thread is now waiting on.
		 * 
		 * @see nachos.threads.ThreadQueue#waitForAccess
		 */
		public void waitForAccess(PriorityQueue waitQueue) {
			// implement me
			if (waitQueue.waitList.contains(thread))
				return;
<<<<<<< HEAD

			waitQueue.waitList.add(thread);
			this.waitQueue = waitQueue;
			this.waitingSince = System.currentTimeMillis();

			/**
			 * if the thread has been running then we want to decrement its priority (which means adding to it for some reason)
			 * by the amount of time it's been running divided by some constant provided in the configuration file
			 */
			if (this.runningSince > 0) {
				long runningFor = (System.currentTimeMillis() - this.runningSince) / agingTime;
				this.priority = (int) Math.min((priority + runningFor), minimumPriority);
			}

=======
			
			waitQueue.waitList.add(thread);
			this.waitQueue = waitQueue;
			this.waitingSince = System.currentTimeMillis();
			
			// if the thread has been run then we want to update its priority based on how long it's been running
			if(this.runningSince > 0){
				int decrement = (int) (System.currentTimeMillis() - this.runningSince);
				this.setPriority(priority - decrement);
			}
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
		}

		/**
		 * Called when the associated thread has acquired access to whatever is
		 * guarded by <tt>waitQueue</tt>. This can occur either as a result of
		 * <tt>acquire(thread)</tt> being invoked on <tt>waitQueue</tt> (where
		 * <tt>thread</tt> is the associated thread), or as a result of
		 * <tt>nextThread()</tt> being invoked on <tt>waitQueue</tt>.
		 * 
		 * @see nachos.threads.ThreadQueue#acquire
		 * @see nachos.threads.ThreadQueue#nextThread
		 */
		public void acquire(PriorityQueue waitQueue) {
			// implement me
<<<<<<< HEAD
			if (!waitQueue.waitList.contains(thread))
				return;

			waitQueue.waitList.remove(thread);
			this.runningSince = System.currentTimeMillis();
			this.waitingSince = -1;
			this.waitQueue = null;
			

=======
			this.runningSince = System.currentTimeMillis();
			waitQueue.waitList.remove(thread);
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
		}

		/** The thread with which this object is associated. */
		protected KThread thread;
		/** The priority of the associated thread. */
		protected int priority;
		protected PriorityQueue waitQueue;
<<<<<<< HEAD
		protected long waitingSince = 0;
		protected long runningSince = 0;
=======
		protected long waitingSince;
		protected long runningSince;
>>>>>>> f5d4e892151cb8f25bc7ce3b31b8f20da957b1ce
	}
}