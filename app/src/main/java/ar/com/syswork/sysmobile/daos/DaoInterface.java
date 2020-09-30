package ar.com.syswork.sysmobile.daos;

import java.util.List;

public interface DaoInterface<T> 
{
	long save(T type);
	void update(T type);
	void delete(T type);
	T getByKey(String key);
	List<T> getAll(String where);
}
