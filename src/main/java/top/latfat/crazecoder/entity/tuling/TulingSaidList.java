package top.latfat.crazecoder.entity.tuling;

import java.util.List;

public class TulingSaidList<T> extends TulingSaid {

	protected List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "list=" + list + "\ncode=" + code + "\ntext=" + text + "\n";
	}
}
