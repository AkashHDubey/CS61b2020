package synthesizer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        this.buffer = new ArrayRingBuffer<Double>((int) Math.round(SR/frequency));

        for(int i = 1;i <= this.buffer.capacity();i++){
            this.buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        for(int i = 1;i <= this.buffer.capacity();i++){
            this.buffer.dequeue();
        }

        for(int i = 1;i <= this.buffer.capacity();i++){
            this.buffer.enqueue(Math.random() - 0.5);
        }

    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {

        Double sample1 = this.buffer.dequeue();
        Double sample2 = this.sample();

        this.buffer.enqueue(DECAY*0.5*((sample1 + sample2)));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {

        return this.buffer.peek();
    }
}
