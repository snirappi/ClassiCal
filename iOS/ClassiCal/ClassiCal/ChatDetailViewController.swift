//
//  ChatDetailViewController.swift
//  ClassiCal
//
//  Created by ZengJintao on 3/31/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class ChatDetailViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    var chatDetail : [Chat] = [
        Chat(user: "John", content: "wow", time: "11"),
        Chat(user: "Tim", content: "It's amazing", time: "12")
    ]
    
    @IBOutlet weak var chatContent: UITableView!
    @IBOutlet weak var myReplyField: UITextField!
    @IBAction func sendMyRely(sender: UIButton) {
        let Reply = myReplyField.text
        if Reply != "" {
            let newReply = Chat(user: "Me", content: Reply, time: "12")
            newReply.isMe = true
            chatDetail.append(newReply)
            myReplyField.text = ""
            let indexPath = NSIndexPath(forRow: chatDetail.count - 1, inSection: 0)
            chatContent.insertRowsAtIndexPaths([indexPath], withRowAnimation: .Automatic)
        }
        
        
    }
    

    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return chatDetail.count
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("chatcell") as ChatCustomTableViewCell
        if self.chatDetail[indexPath.row].isMe {
            cell.chatContent!.text = ""
            cell.chatName!.text = ""
            cell.chatReport!.text = ""
            cell.myName!.text = "Me"
            cell.myReply!.text = self.chatDetail[indexPath.row].content
        } else {
            cell.chatContent!.text = self.chatDetail[indexPath.row].content
            cell.chatName!.text = self.chatDetail[indexPath.row].user
            cell.myReply!.text = ""
            cell.myName!.text = ""
            
        }

        return cell
    }
    
    
    
}