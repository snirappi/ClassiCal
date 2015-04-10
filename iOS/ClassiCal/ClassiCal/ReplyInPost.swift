//
//  ReplyInPost.swift
//  ClassiCal
//
//  Created by ZengJintao on 3/23/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class ReplyInPost: UITableViewController {
    
    var replyList1 = [
        Reply(name: "Tim", content: "This is a test content", time: "May 10"),
        Reply(name: "baa", content: "This is a what", time: "Aug 10"),
        Reply(name: "John", content: "hehe", time: "Sep 10")
    ]
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return replyList1.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("ListViewCell", forIndexPath: indexPath) as UITableViewCell
        let item = replyList1[indexPath.row]
        cell.textLabel?.text = item.content
        return cell
    }

}