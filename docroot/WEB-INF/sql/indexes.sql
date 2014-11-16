create unique index IX_9738C4E2 on Limsmuc_Conversation (conversationId);
create index IX_C3E2CA4B on Limsmuc_Conversation (syncIdSUC);

create index IX_BC48D7E8 on Limsmuc_Message (cid);
create index IX_21E2C551 on Limsmuc_Message (creatorId);
create index IX_2D392735 on Limsmuc_Message (syncIdSUC);

create index IX_90E2497D on Limsmuc_Panel (userId);

create index IX_E6C333BC on Limsmuc_Participant (cid);
create unique index IX_6AEA59A2 on Limsmuc_Participant (cid, participantId);
create index IX_8D897FAC on Limsmuc_Participant (participantId);
create index IX_8A3FED13 on Limsmuc_Participant (participantId, isOpened);

create index IX_A8B4EE3F on Limsmuc_Settings (presence);
create unique index IX_8F36E54A on Limsmuc_Settings (userId);