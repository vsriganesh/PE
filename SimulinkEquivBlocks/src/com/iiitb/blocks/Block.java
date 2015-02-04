package com.iiitb.blocks;

import com.iiitb.cfg.Accfg;
import com.iiitb.inter.IBlock;

public abstract class Block implements IBlock {

	private String name;
	private String value;
	private int sign;
	private Accfg accfg;

	boolean inputSetFlag;

	public boolean isInputSetFlag() {
		return inputSetFlag;
	}

	public void setInputSetFlag(boolean inputSetFlag) {
		this.inputSetFlag = inputSetFlag;
	}

	public Accfg getAccfg() {
		return accfg;
	}

	public void setAccfg(Accfg accfg) {
		this.accfg = accfg;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Block(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	public Block() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return getName();

	}

	@Override
	public boolean equals(Object block) {

		if (block instanceof Block) {

			if (this.getName().equalsIgnoreCase(((Block) block).getName())) {

				return true;
			}

		}
		return false;
	}

}
