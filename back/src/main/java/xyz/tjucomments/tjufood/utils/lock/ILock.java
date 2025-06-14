package xyz.tjucomments.tjufood.utils.lock;

public interface ILock {
    boolean tryLock(long timeoutSec);
    void unlock();
}