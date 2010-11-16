eventStatusFinal = { msg ->
	try {
		msg.grom()
	} catch (Exception e) {}
}

eventCompileStart = { kind ->
	try {
		"recompiling and restarting application...".grom()
	} catch (Exception e) {}
}

eventStatusError = { msg ->
	msg.grom()
}