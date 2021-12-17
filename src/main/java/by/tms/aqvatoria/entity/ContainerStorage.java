package by.tms.aqvatoria.entity;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContainerStorage {
    private static final Logger logger = LogManager.getLogger();
    private static final int STORAGE_CAPACITY = 500;
    private static final ContainerStorage instance = new ContainerStorage();
    private AtomicInteger currentCapacity = new AtomicInteger();
    private final Lock lock = new ReentrantLock(true);

    public static ContainerStorage getInstance() {
        return instance;
    }

    public void loadContainers(Ship ship) throws AqvatoriaThreadException {
        try {
            lock.lock();
            while (currentCapacity.get() > 0 && ship.ableToAdd()) {
                logger.info("ship {}, load containers", ship.getId());
                TimeUnit.MILLISECONDS.sleep(100000);
                currentCapacity.decrementAndGet();
                ship.addContainers();
            }
            logger.info("Ship {}, loading", ship.getId());
            logger.info("Storage capacity {} ", currentCapacity.get());
        } catch (InterruptedException e) {
            throw new AqvatoriaThreadException(e);
        } finally {
            lock.unlock();
        }
    }

    public void unloadContainers(Ship ship) throws AqvatoriaThreadException {
        try {
            lock.lock();
            while (currentCapacity.get() < STORAGE_CAPACITY && ship.ableToDelete()) {
                logger.info("Ship {}, unloads containers", ship.getId());
                TimeUnit.MILLISECONDS.sleep(1000);
                currentCapacity.incrementAndGet();
                ship.deleteContainers();
            }
            logger.info("Ship {}, complite unload", ship.getId());
            logger.info("Storage capacity {}", currentCapacity.get());
        } catch (InterruptedException e) {
            throw new AqvatoriaThreadException(e);
        } finally {
            lock.unlock();
        }
    }
}
