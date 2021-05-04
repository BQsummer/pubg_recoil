package com.bqsummer.interception;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {
	
	Kernel32 k32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

	int HIGH_PRIORITY_CLASS = 0x00000080;

	boolean SetPriorityClass(Pointer hProcess, int dwPriorityClass);

	Pointer GetCurrentProcess();
}