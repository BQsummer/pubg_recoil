package com.bqsummer.interception;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface Interception extends Library {

	Interception interception = (Interception) Native.loadLibrary("interception", Interception.class);

	int interception_is_keyboard(int device);

	int interception_is_mouse(int device);

	Pointer interception_create_context();

	// void raise_process_priority();
	// void interception_set_filter(InterceptionContext context,
	// InterceptionPredicate predicate, InterceptionFilter filter);
	void interception_set_filter(Pointer context, Callback predicate, short filter);

	// int INTERCEPTION_API interception_receive(InterceptionContext context,
	// InterceptionDevice device, InterceptionStroke *stroke, unsigned int nstroke);
	int interception_receive(Pointer context, int device, InterceptionKeyStroke stroke, int nstroke);

	//int interception_receive(Pointer context, int device, MouseStroke stroke, int nstroke);

	// InterceptionDevice INTERCEPTION_API interception_wait(InterceptionContext
	// context);
	int interception_wait(Pointer context);

	// int INTERCEPTION_API interception_send(InterceptionContext context,
	// InterceptionDevice device, const InterceptionStroke *stroke, unsigned int
	// nstroke);
	int interception_send(Pointer context, int device, InterceptionKeyStroke stroke, int nstroke);

	int interception_send(Pointer context, int device, MouseStroke stroke, int nstroke);

	// void INTERCEPTION_API interception_destroy_context(InterceptionContext
	// context);
	void interception_destroy_context(Pointer context);
}
