package by.tms.aqvatoria.entity;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Portus {
    private static final Logger logger = LogManager.getLogger();
    private static final int BERTH_CAPACITY = 8;
    private static final Portus instance = new Portus();
    private static AtomicBoolean portCreated = new AtomicBoolean();
    private Queue<Berth> freeBerths = new LinkedList<>();
    private Queue<Berth> busyBerths = new LinkedList<>();
    private static final Lock lock = new ReentrantLock(true);
    private static final Lock berthLock = new ReentrantLock(true);
    private final Condition berthIsFree = berthLock.newCondition();

    public static Portus getInstance() {
        return instance;
    }

    private Portus() {
        for (int i = 0; i < BERTH_CAPACITY; i++) {
            freeBerths.add(new Berth(UUID.randomUUID()));
        }
    }

    public Berth getFreeBerth() throws AqvatoriaThreadException {
        try {
            berthLock.lock();
            while (freeBerths.isEmpty()) {
                berthIsFree.await();
            }
            Berth berth = freeBerths.poll();
            busyBerths.offer(berth);
            return berth;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            throw new AqvatoriaThreadException("", e);
        } finally {
            berthLock.unlock();
        }
    }

    public void leaveBerth(Berth berth) {
        try {
            berthLock.lock();
            freeBerths.offer(berth);
            busyBerths.remove();
            berthIsFree.signal();
        } finally {
            berthLock.unlock();
        }
    }
}
