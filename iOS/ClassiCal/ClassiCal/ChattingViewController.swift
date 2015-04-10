//
//  SecondViewController.swift
//  ClassiCal
//
//  Created by 曾 锦涛 on 2/17/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import UIKit

class ChattingViewController: UITableViewController, UINavigationControllerDelegate {
    
    var courses = ["cs250", "cs307"]
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "toChatDetail" {
            let indexPath = tableView.indexPathForSelectedRow()
            let chatDetailViewController = segue.destinationViewController as ChatDetailViewController
            //var a = courses[indexPath!.row]
            chatDetailViewController.title = courses[indexPath!.row]
            println("prepare for segue 1\n\n\n")
            //chatDetailViewController.chatDetail = courses[indexPath!.row]
            
        }
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return courses.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("ListViewCell", forIndexPath: indexPath) as UITableViewCell
        
        let item = courses[indexPath.row]
        cell.textLabel?.text = item
        return cell
        
    }
    
    
    
}
