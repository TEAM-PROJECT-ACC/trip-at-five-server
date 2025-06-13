package com.kh.clock.chat.repository;

import java.util.ArrayList;
import com.kh.clock.chat.domain.ChatMessage;
import com.kh.clock.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
  private ChatRoom chatRoom;
  private ArrayList<ChatMessage> messages;
}
