create table Limsmuc_Conversation (
	cid LONG not null primary key,
	conversationId VARCHAR(256) null,
	conversationType INTEGER,
	updatedAt DATE null,
	syncIdSUC LONG
);

create table Limsmuc_Message (
	mid LONG not null primary key,
	cid LONG,
	messageType INTEGER,
	creatorId LONG,
	createdAt DATE null,
	body TEXT null,
	syncIdSUC LONG,
	syncIdChatPortlet LONG
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
	isCreator BOOLEAN,
	hasLeft BOOLEAN,
	openedAt DATE null
);

create table Limsmuc_Settings (
	sid LONG not null primary key,
	userId LONG,
	presence VARCHAR(75) null,
	presenceUpdatedAt DATE null,
	mute BOOLEAN,
	chatEnabled BOOLEAN,
	adminAreaOpened BOOLEAN,
	connected BOOLEAN,
	connectedAt DATE null
);

create table Limsmuc_Synchronization (
	sid LONG not null primary key,
	sucSync BOOLEAN
);