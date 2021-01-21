package Test;

public class Person {

	private Object key1 = new Object();
	private Object key2 = new Object();

	public void doSomethingOne() throws InterruptedException {

		synchronized (key1) {

			System.out.println("Entered in doSomethingOne and doing Work" + Thread.currentThread());
			doSomethingTwo();

		}

	}

	public void doSomethingTwo() throws InterruptedException {

		synchronized (key2) {
			System.out.println("Entered in doSomethingTwo and doing Work"+ Thread.currentThread());
			doSomethingThree();

		}

	}

	public void doSomethingThree() {

		synchronized (key1) {
			System.out.println("Entered in doSomethingThree and doing Work"+ Thread.currentThread());

		}
	}

}
