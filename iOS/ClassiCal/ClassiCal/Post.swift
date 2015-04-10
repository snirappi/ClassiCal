//
//  Post.swift
//  ClassiCal
//
//  Created by 曾 锦涛 on 3/6/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class Post: NSObject {
    var title: String
    var content: String
    var replyList: [Reply]
    init(title: String, content: String) {
        self.title = title
        self.content = content
        self.replyList = [
            Reply(name: "Tim", content: "This is a test content", time: "May 10"),
            Reply(name: "baa", content: "This is a what", time: "Aug 10"),
            Reply(name: "John", content: "hehe", time: "Sep 10")
        ]
    }
    
    func addReply(name: String, content: String, time: String) {
        let reply = Reply(name: name, content: content, time: time)
        replyList.append(reply)
    }
}
