package com.chat.server.view;

public enum MessageType {
	DIRECT, // message to a single user
	GROUP,  // message to a single chat room or group
	MASS_DIRECT; // message to multiple users
}
