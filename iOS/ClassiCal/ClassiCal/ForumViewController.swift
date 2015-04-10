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
        Post(title: "When is the 1st hw due?", content: "It is not on course page or blackboard!! and let me test how long can the text field be and what will happen")
    ]
    var forumList2 = [
        Post(title: "This is forumList2", content: "It is not on course page or blackboard!! and let me test how long can the text field be and what will happen")
    ]
    
    var courseList = ["CS250", "CS307"]
    
    var forumChose = "CS250"
    
    @IBAction func unwindToForum(segue: UIStoryboardSegue) {
        if segue.identifier == "DoneItem" {
            let addPost = segue.sourceViewController as AddPost
            if let newPost = addPost.newItem {
                forumList += [newPost]
                let indexPath = NSIndexPath(forRow: forumList.count - 1, inSection: 0)
                tableView.insertRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
            }
        } else if segue.identifier == "CourseSelected" {
            let courseSelect = segue.sourceViewController as ForumSelectCourse
            forumChose = courseSelect.courseChose
            println("user choose \(forumChose)")
        }
    }
    
    
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "toPostDetail" {
            let indexPath = tableView.indexPathForSelectedRow()
            let postViewController = segue.destinationViewController as PostViewController
            println("prepare for segue 1\n\n\n")
            postViewController.postDetail = forumList[indexPath!.row]
            
            
        } else if segue.identifier == "toCourseSelect" {
            println("toCourseSelect")
            let forumCourseSelect = segue.destinationViewController as ForumSelectCourse
            println("toCourseSelect2")
            forumCourseSelect.courseList = courseList
            println("toCourseSelect3")
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
        
        return cell
        
    }

    override func viewDidLoad() {
        //self.title = forumChose
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

}

