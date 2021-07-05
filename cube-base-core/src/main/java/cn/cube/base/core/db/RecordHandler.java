package cn.cube.base.core.db;

public interface RecordHandler<T> {

	void process(T record);

}
