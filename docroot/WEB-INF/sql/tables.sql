create table Limsmuc_Conversation (
	cid LONG not null primary key,
	conversationId VARCHAR(256) null,
	conversationType VARCHAR(75) null,
	updatedAt DATE null
);

create table Limsmuc_Message (
	mid LONG not null primary key,
	cid LONG,
	creatorId LONG,
	createdAt LONG,
	body TEXT null
);

create table Limsmuc_Panel (
	pid LONG not null primary key,
	userId LONG,
	activePanelId VARCHAR(256) null
);

create table Limsmuc_Participant (
	pid LONG not null primary key,
	cid LONG,
	participantId LONG,
	unreadMessagesCount INTEGER,
	isOpened BOOLEAN,
	openedAt LONG
);

create table Limsmuc_Settings (
	sid LONG not null primary key,
	userId LONG,
	presence VARCHAR(75) null,
	presenceUpdatedAt LONG,
	mute BOOLEAN,
	chatEnabled BOOLEAN,
	adminAreaOpened BOOLEAN
);