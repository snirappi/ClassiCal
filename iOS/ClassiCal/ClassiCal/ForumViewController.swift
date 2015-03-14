//
//  SecondViewController.swift
//  ClassiCal
//
//  Created by 曾 锦涛 on 2/17/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//
import Foundation
import UIKit

class ForumViewController: UITableViewController, UINavigationControllerDelegate{
    
    var forumList = [
        Post(title: "When is the 1st hw due?", content: "It is not on course page or blackboard!!")
    ]

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func unwindToForum(segue: UIStoryboardSegue) {
        if segue.identifier == "DoneItem" {
            let addPost = segue.sourceViewController as AddPost
            if let newPost = addPost.newItem {
                forumList += [newPost]
                
                let indexPath = NSIndexPath(forRow: forumList.count - 1, inSection: 0)
                tableView.insertRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
            }
        } else if segue.identifier == "toPostDetail" {
            let indexPath = tableView.indexPathForSelectedRow()
            let postController = segue.destinationViewController as PostViewController
            //webViewController.DetailID = DataSource[indexPath!.row].NewsUrl
            //postController.
        }
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return forumList.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("ListViewCell", forIndexPath: indexPath) as UITableViewCell
        
        let item = forumList[indexPath.row]
        cell.textLabel?.text = item.title
        cell.detailTextLabel?.text = item.content
        //cell.textLabel?.subviews = item.content
        
        /*if item.completed {
            cell.accessoryType = .Checkmark
            cell.imageView?.image = item.photo
        } else {
            cell.accessoryType = .None
            cell.imageView?.image = nil
        }*/
        
        return cell
        
    }

    

}

