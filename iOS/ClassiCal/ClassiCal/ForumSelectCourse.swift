//
//  ForumSelectCourse.swift
//  ClassiCal
//
//  Created by ZengJintao on 4/2/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class ForumSelectCourse: UITableViewController {
    
    var courseList:[String] = []
    var courseChose: String = ""
    
    @IBAction func cancel(sender: AnyObject) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return courseList.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("ListViewCell", forIndexPath: indexPath) as UITableViewCell
        let item = courseList[indexPath.row]
        cell.textLabel?.text = item
        return cell
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        println("o ye")
        if segue.identifier == "CourseSelected" {
            let indexPath = tableView.indexPathForSelectedRow()
            courseChose = courseList[indexPath!.row]
        }
    }
    
    
}