//
//  Chat.swift
//  ClassiCal
//
//  Created by ZengJintao on 4/1/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
class Chat: NSObject {
    var user: String
    var content: String
    var time: String
    var isMe: Bool
    init(user: String, content: String, time: String) {
        self.user = user
        self.content = content
        self.time = time
        self.isMe = false
    }
}